(ns maru.ghost.confucius.core
  (:refer-clojure :exclude [name])
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.gtp.core :as gtp])
  (:require [maru.common.game.core :as game])
  (:require [maru.common.game.log.core :as log])
  (:require [maru.common.game.rule.core :as rule])
  (:require [maru.common.pattern.core :as pattern])
  (:require [maru.common.sgf.core :as sgf])
  (:use [maru.common.utility.core :only
  [string-from-point string-to-digit point-from-string string-to-color]]))

(defn- random-legal-move-generator [color state]
  (let [moves (rule/all-legal-moves color state)]
    (if (empty? moves) "PASS"
      (string-from-point (rand-nth moves) (:size state)))))

(defn- pattern-move-generator [color size log]
  (pattern/next-move
    (pattern/from-sgf (sgf/parse-file "dict/joseki/9x9/001.sgf") size) log))

(defn- move-generator [color state]
  (let [move (pattern-move-generator color (:size state) (:log state))]
    (if (= move -1)
      (random-legal-move-generator color state)
      (string-from-point move (:size state)))))

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
    (assoc (game/play state x y color)
      :log (log/ging (:log state) point)
      :message (str "play " color " at " string)))))

(defn clear-board [state]
  (assoc state :message "clear"))

(defn genmove [state color]
  (let [color (string-to-color color)
        move (move-generator color state)]
    (if (= move "PASS") (assoc state :message "PASS")
      (let [point (point-from-string move (:size state))
            x (board/to-x point (:size state))
            y (board/to-y point (:size state))]
        (assoc (game/play state x y color)
          :log (log/ging (:log state) point)
          :message move)))))
