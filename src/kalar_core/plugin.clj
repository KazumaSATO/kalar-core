(ns kalar-core.plugin)

(defprotocol KalarPlugin
  (load-plugin [this] "load"))

(defmacro defkalarplugin [name & opts+specs]
  `(def ~name (reify ~@opts+specs)))