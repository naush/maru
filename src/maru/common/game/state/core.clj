(ns maru.common.game.state.core)

(def komi 6.5)
(def size 19)
(def ko -999)
(def board (vec (repeat (* size size) 0)))

(defn set-komi [points] (def komi points))
(defn set-size [size]
  (def size size)
  (def board (vec (repeat (* size size) 0))))
(defn set-ko [ko] (def ko ko))
(defn set-board [board] (def board board))
