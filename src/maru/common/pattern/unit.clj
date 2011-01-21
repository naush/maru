(ns maru.common.pattern.unit
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.sgf.core :as sgf])
  (:use [maru.common.pattern.core] :reload)
  (:use [clojure.test]))

(deftest convert-sgf-node-to-pattern
  (state/set-size 5)
  (let [node (sgf/parse-file "dict/joseki/5x5/001.sgf")]
    (is (= (list 0 6 12 18)
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
