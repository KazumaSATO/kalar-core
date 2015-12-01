(ns kalar-core.file.tracker
  (:require [clojure.java.io :as io]
            [clojure.data :as data]
            ))



(defn create-file-lastmodtime-map [directory]
  (letfn
    [(create-map [efile]
       {(keyword (.getAbsolutePath efile)) (.lastModified efile)})
     (merge-map [efiles]
       (reduce #(merge %1 (create-map %2)) {} efiles))
     (create-childmap [dir]
       (let [children (.listFiles dir)
             thismap (merge-map children)
             child-dirs (filter #(.isDirectory %) children)]
         (reduce #(merge %1 %2) thismap
                 (for [m child-dirs] (create-childmap m))))
       )]
    (create-childmap (io/file directory))))

(defn find-removed [old current]
  (keys (first (data/diff old current))))
(defn find-created [old current]
  (keys (nth (data/diff old current) 1)))

(defn tracking
  ([root] (tracking root (create-file-lastmodtime-map root)))
  ([root timestamp-map]
   (let [timemap (atom timestamp-map)]
     (fn []
       (let [then @timemap
             now (create-file-lastmodtime-map root)
             diff {:removed (find-removed then now)
                   :created (find-created then now)}]
         (reset! timemap now)
         diff)))))
;java files si


(defn create-filemod-map [file]
  {(keyword (.getAbsolutePath file)) (.lastModified file)})

(defn concat-filemod-maps [files]
  (reduce #(merge %1 (create-filemod-map %2)) {} files))

(defn list-children [dir]
  (.listFiles dir))

(defn list-dir-children [dir]
  (filter #(.isDirectory %1) (.listFiles dir)))


