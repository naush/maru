(ns maru.common.game.info)

(def komi 7.5)
(def size 19)

(defn set-komi [points] (def komi points))
(defn set-size [size] (def size size))
