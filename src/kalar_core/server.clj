(ns kalar-core.server
  (:require [kalar-core.config :refer [read-config]]
            [kalar-core.plugin :as plugin]
            [kalar-core.file.tracker :as tracker]
             [compojure.core :refer [GET defroutes]]
             [compojure.route :as route]
             [ring.util.response :refer [redirect]]
             ))

(defn- load-plugins []
  (dorun (for [plugin (-> (read-config) :plugins)]
           (let [nmspc (-> (re-seq #"^[^/]*" (str plugin)) first symbol)]
             (require nmspc)
             (plugin/load-plugin (var-get (resolve plugin)))))))

(defn init []
  (load-plugins))



(defroutes handler
  (GET ":prefix{.*}/" [prefix] (redirect (str prefix "/index.html")))
  (route/resources "/" {:root (:dest (read-config))})
  (route/not-found "Page not found"))

(def ^{:private true} track (tracker/track "resources"))

(defn- wrap-tracker [handler]
  (fn [request]
    (let [diff (track)]
      (if (not (and (empty? (:removed diff)) (empty? (:created diff))))
        (load-plugins))
      (handler request))))


(def app (wrap-tracker handler))
