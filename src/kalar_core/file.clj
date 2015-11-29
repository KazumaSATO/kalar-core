(ns kalar-core.file
  (:require [clojure.java.io :as io]
            [kalar-core.config :as config]))

(defn find-dest []
  (let [f (io/file "resources" (:dest (config/read-config)))]
    (.getAbsolutePath f)))

(defn prepare-write-file [maybe-unexist-file]
  (.mkdirs (.getParentFile maybe-unexist-file))
  (.createNewFile maybe-unexist-file))
