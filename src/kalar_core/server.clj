(ns kalar-core.server
  (:require [kalar-core.config :refer [read-config]]
            [kalar-core.plugin :as plugin]
             [compojure.core :refer [GET defroutes]]
             [compojure.route :as route]
             [ring.util.response :refer [redirect]]
             ))

(defn load-plugins [plugins]
  (dorun (for [plugin plugins]
           (let [nmspc (-> (re-seq #"^[^/]*" (str plugin)) first symbol)]
             (require nmspc)
             (println plugin)
              (println (plugin/load-plugin (var-get (resolve plugin)) ))))))

(defn init []
  (let [config (read-config)
        plugins (-> config :plugins)]
    (load-plugins plugins)))



(defroutes handler
  (GET ":prefix{.*}/" [prefix] (redirect (str prefix "/index.html")))
  (route/resources "/" {:root "_site"})
  (route/not-found "Page not found"))