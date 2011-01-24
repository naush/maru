(ns maru.common.game.state.core
  (:use [maru.common.utility.core :only [insert-last]]))

(def default
  (hash-map :komi 6.5
	          :size 19
	          :message ""
	          :board (vec (repeat (* 19 19) 0))
	          :ko -999))

(def log ())

(defn logging [move] (def log (insert-last log move)))
(defn empty-log [] (def log ()))
