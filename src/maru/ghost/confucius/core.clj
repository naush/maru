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

(defn- random-legal-move-generator [color size board ko]
  (let [moves (rule/all-legal-moves board color size ko)]
    (if (empty? moves) "PASS"
      (string-from-point (rand-nth moves) size))))

(defn- pattern-move-generator [color size]
  (pattern/next-move
    (pattern/from-sgf (sgf/parse-file "dict/joseki/9x9/001.sgf") size)
    state/log))

(defn- move-generator [color size board ko]
  (let [move (pattern-move-generator color size)]
    (if (= move -1)
      (random-legal-move-generator color size board ko)
      (string-from-point move size))))

(defn name [state] (assoc state :message "Confucius"))

(defn version [state] (assoc state :message "4.0"))

(defn boardsize [state size]
  (let [size (string-to-digit size)]
    (assoc state :size size :message (str "size: " size))))

(defn komi [state points]
  (assoc state :komi points
	             :message (str "komi: " points)))

(defn play [state color string]
  (if (= string "PASS") ""
  (let [point (point-from-string string (:size state))
        x (board/to-x point (:size state))
        y (board/to-y point (:size state))
        color (string-to-color color)]
    (state/logging point)
    (assoc (game/play state x y color) :message (str "play " color " at " string)))))

(defn clear-board [state]
  (assoc state :message "clear"))

(defn genmove [state color]
  (let [color (string-to-color color)
        move (move-generator color (:size state) (:board state) (:ko state))]
    (if (= move "PASS") (assoc state :message "PASS")
      (let [point (point-from-string move (:size state))
            x (board/to-x point (:size state))
            y (board/to-y point (:size state))]
        (state/logging point)
        (assoc (game/play state x y color) :message move)))))
