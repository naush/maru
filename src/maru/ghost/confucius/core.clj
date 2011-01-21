(ns maru.ghost.confucius.core
  (:refer-clojure :exclude [name])
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.gtp.core :as gtp])
  (:require [maru.common.game.core :as game])
  (:require [maru.common.game.rule.core :as rule])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.pattern.core :as pattern])
  (:require [maru.common.sgf.core :as sgf])
  (:use [maru.common.utility.core :only
  [string-from-point string-to-digit point-from-string string-to-color]]))

(defn random-legal-move-generator [color]
  (let [moves (rule/all-legal-moves state/board color)]
    (if (empty? moves) "PASS"
      (string-from-point (rand-nth moves) state/size))))

(defn pattern-move-generator [color]
  (pattern/next-move
    (pattern/from-sgf (sgf/parse "(;B[AA];W[BB](;B[CC];W[DD]))"))
    state/log))

(defn move-generator [color]
  (let [move (pattern-move-generator color)]
    (if (= move -1)
      (random-legal-move-generator color)
      (string-from-point move state/size))))

(defn name [] (str "Confucius"))

(defn version [] (str "3.0"))

(defn boardsize [size]
  (let [size (string-to-digit size)]
    (state/set-size size)))

(defn komi [points] (state/set-komi points))

(defn play [color string]
  (if (= string "PASS") ""
  (let [point (point-from-string string state/size)
        x (board/to-x point)
        y (board/to-y point)
        color (string-to-color color)]
    (state/logging point)
    (game/play state/board x y color)
    (state/set-board state/board) state/board)))

(defn clear-board []
  (state/set-size state/size))

(defn genmove [color]
  (let [color (string-to-color color)
        move (move-generator color)]
    (if (= move "PASS") "PASS"
      (let [point (point-from-string move state/size)
            x (board/to-x point)
            y (board/to-y point)]
        (state/logging point)
        (game/play state/board x y color)
        (state/set-board state/board)
        move))))
