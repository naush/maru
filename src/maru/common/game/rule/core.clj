(ns maru.common.game.rule.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.group.core :as group])
  (:require [maru.common.game.state.core :as state]))

(defn dead? [group] (= (count (:liberties group)) 0))

(defn alive? [group] (not (dead? group)))

(defn suicide? [color x y state]
  (let [point (board/to-point x y (:size state))
        current-board (board/set-stone (:board state) point color)
        enemies (group/find-enemies current-board point color (:size state))
        ally (group/find-ally current-board point color (:size state))]
    (and (reduce #(and %1 (> (count (:liberties %2)) 0)) true enemies)
         (= (count (:liberties ally)) 0))))

(defn legal? [color x y state]
  (and (not (= (:ko state) (board/to-point x y (:size state))))
       (not (board/out-of-bound x y (:size state)))
       (board/open? (:board state) x y (:size state))
       (not (suicide? color x y state))))

(defn all-legal-moves [color state]
  (let [size (:size state)]
    (filter #(legal? color
	           (board/to-x % size) (board/to-y % size) state)
             (range 0 (* size size)))))
