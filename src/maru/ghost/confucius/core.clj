(ns maru.ghost.confucius.core
  (:refer-clojure :exclude [name])
  (:require [maru.common.game.board.core :as board] :reload)
  (:require [maru.common.game.stone.core :as stone] :reload)
  (:require [maru.common.game.info.core :as info] :reload)
  (:require [maru.common.gtp.core :as gtp] :reload)
  (:require [maru.common.utility.core :as utility]))

(defn random-move-generator [color]
  (let [max info/size]
    (str (char (rand-nth (range 65 (+ 64 max)))) (rand-nth (range 1 max)))))

(defn name [] (str "Confucius"))

(defn version [] (str "2.0"))

(defn boardsize [size]
  (let [size (utility/string-to-digit size)]
    (board/reset size)
    (info/set-board board/empty)
    (info/set-size size)))

(defn komi [points] (info/set-komi points))

(defn play [color position]
  (let [pos (utility/pos-from-string position board/size)
        color (board/string-to-color color)
        new-board (board/set-stone info/board pos color)]
    (info/set-board new-board) (str new-board)))

(defn clear-board [] (board/reset board/size))

(defn genmove [color]
  (let [move (random-move-generator color)
        color (board/string-to-color color)
        pos (utility/pos-from-string move board/size)]
    (info/set-board (board/set-stone info/board pos color)) move))
