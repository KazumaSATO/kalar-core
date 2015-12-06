(ns kalar-core.file
  (:require [clojure.java.io :as io]
            [kalar-core.config :as config]))

(defn find-resources-dir []
  "DEPRECATED"
  (.getAbsolutePath (io/file "resources")))

(defn find-dest []
  "returns a build destination as string."
  (let [f (io/file (find-resources-dir) (:dest (config/read-config)))]
    (.getAbsolutePath f)))

(defn touch [maybe-unexist-file]
  (.mkdirs (.getParentFile maybe-unexist-file))
  (.createNewFile maybe-unexist-file))


