(ns maru.ghost.confucius.core
  (:refer-clojure :exclude [name])
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.gtp.core :as gtp])
  (:require [maru.common.game.core :as game])
  (:require [maru.common.game.rule.core :as rule])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.sgf.core :as sgf])
  (:require [maru.common.utility.core :as utility]))

(defn random-legal-move-generator [color]
  (let [moves (rule/all-legal-moves state/board color)]
    (if (empty? moves) "PASS"
      (utility/string-from-point (rand-nth moves) state/size))))

(defn name [] (str "Confucius"))

(defn version [] (str "3.0"))

(defn boardsize [size]
  (let [size (utility/string-to-digit size)]
    (state/set-size size)))

(defn komi [points] (state/set-komi points))

(defn play [color string]
  (if (= string "PASS") ""
  (let [point (utility/point-from-string string state/size)
        x (board/to-x point)
        y (board/to-y point)
        color (utility/string-to-color color)]
    (game/play state/board x y color)
    (state/set-board state/board) (str state/board))))

(defn clear-board []
  (state/set-size state/size))

(defn genmove [color]
  (let [color (utility/string-to-color color)
        move (random-legal-move-generator color)]
    (if (= move "PASS") "PASS"
      (let [point (utility/point-from-string move state/size)
            x (board/to-x point)
            y (board/to-y point)]
        (game/play state/board x y color)
        (state/set-board state/board)
        move))))
