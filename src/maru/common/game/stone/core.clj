(ns maru.common.game.stone.core)

(defn craft [pos color]
  (hash-map :pos pos :color color))
