(ns kalar-core.server
  (:require [kalar-core.config :refer [read-config]]))

(defn init []
 (println (read-config)))