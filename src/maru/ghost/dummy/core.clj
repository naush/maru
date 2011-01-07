(ns maru.ghost.dummy.core
  (:refer-clojure :exclude [name])
  (:require [maru.common.board.core :as board] :reload)
  (:require [maru.common.game.info :as info] :reload)
  (:require [maru.common.gtp.core :as gtp] :reload)
  (:require [maru.common.utility.core :only [string-to-integer] :as utility] :reload))

(defn random-move-generator [color]
  (let [max info/size]
    (str (char (rand-nth (range 65 (+ 64 max)))) (rand-nth (range 1 max)))))

(defn name [] (str "dummy"))
(defn version [] (str "0.6"))
(defn boardsize [size] (info/set-size (utility/string-to-integer size)))
(defn komi [points] (info/set-komi points))
(defn play [color position] (str "set " color " at " position))
(defn clear-board [] (str ""))
(defn genmove [color] (random-move-generator color)) ; return a string of letter and digit, eg. H12
