(ns kalar-core.server
  (:require [kalar-core.config :refer [read-config]]))

(defn init []
  (let [config (read-config)
        plugins (-> config :plugins)
        lst (nth plugins 0)
        f (nth lst 0)
        a (nth lst 1)]
    (println f)
    (println a)
    (-> 'kalar-core.server/init resolve println) ))