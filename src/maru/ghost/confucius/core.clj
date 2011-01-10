(ns maru.ghost.confucius.core
  (:refer-clojure :exclude [name])
  (:require [maru.common.game.board.core :as board] :reload)
  (:require [maru.common.game.stone.core :as stone] :reload)
  (:require [maru.common.game.info.core :as info] :reload)
  (:require [maru.common.gtp.core :as gtp] :reload)
  (:require [maru.common.utility.core :only [string-to-digit] :as utility]))

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
(defn play [color position] (info/set-board (board/play info/board (board/pos-from-string position) (board/string-to-color color))) (str info/board))
(defn clear-board [] (str ""))
(defn genmove [color] (random-move-generator color))
