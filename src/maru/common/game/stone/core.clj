(ns maru.common.game.stone.core)

(defn craft [point color]
  (hash-map :point point :color color))
