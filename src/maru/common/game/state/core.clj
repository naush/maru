(ns maru.common.game.state.core)

(def default
  (hash-map :komi 6.5
	          :size 19
	          :message ""
	          :board (vec (repeat (* 19 19) 0))
	          :ko -999
	          :log ()))
