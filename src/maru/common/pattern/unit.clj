(ns maru.common.pattern.unit
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.sgf.core :as sgf])
  (:use [maru.common.pattern.core] :reload)
  (:use [clojure.test]))

(deftest convert-sgf-node-to-pattern
  (state/set-size 5)
  (let [node (sgf/parse-file "dict/joseki/5x5/001.sgf")]
    (is (= (list 0 6 12 13)
           (from-sgf node)))))

(deftest given-pattern-and-consecutively-matched-log-return-next-move
  (state/set-size 5)
  (let [pattern (list 0 1 2 3) log (list 0 1 2)]
    (is (= 3 (next-move pattern log)))))

(deftest given-pattern-and-unconsecutively-matched-log-return-next-move
  (state/set-size 5)
  (let [pattern (list 0 1 2 3) log (list 0 21 1 22 2)]
    (is (= 3 (next-move pattern log)))))

(deftest given-pattern-already-exists-in-log
  (state/set-size 5)
  (let [pattern (list 0 1) log (list 0 1 2 3)]
    (is (= -1 (next-move pattern log)))))

(deftest given-pattern-and-log-are-identical
  (state/set-size 5)
  (let [pattern (list 0 1) log (list 0 1)]
    (is (= -1 (next-move pattern log)))))

(deftest given-pattern-and-swap
  (state/set-size 5)
  (is (= 10 (swap 2))))

(deftest given-pattern-and-flip-in-x-direction
  (state/set-size 5)
  (is (= 14 (flip-x 10))))

(deftest given-pattern-and-flip-in-y-direction
  (state/set-size 5)
  (is (= 16 (flip-y 6))))

(deftest given-pattern-and-flip-in-x-and-then-y-direction
  (state/set-size 5)
  (is (= 23 (flip 1))))

(deftest match-multiple-patterns-and-return-all-matched-moves
  (state/set-size 5)
  (let [patterns (list (list 0 1 2) (list 0 1 3)) log (list 0 1)]
    (is (= (list 2 3) (next-moves patterns log)))))
