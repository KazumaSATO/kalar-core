(ns kalar-core.config
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn read-config []
  (println (-> "config.edn" io/resource io/file slurp read-string)))

