(ns kalar-protocol.core)

(defprotocol KalarPlugin
  (kalar-eval [config]))