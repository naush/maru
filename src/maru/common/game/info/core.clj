(ns maru.common.game.info.core (:require [maru.common.game.board.core :as board]))

(def komi 6.5)
(def size 19)
(def board board/empty)

(defn set-komi [points] (def komi points))
(defn set-size [size] (def size size))
(defn set-board [board] (def board board))
