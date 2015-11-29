(ns kalar-core.plugin)

(defprotocol KalarPlugin
  (load-plugin [this] "load"))

(defmacro defkalar-plugin [name & opts+specs]
  `(def ~name (reify ~@opts+specs)))