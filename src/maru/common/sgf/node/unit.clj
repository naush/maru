(ns maru.common.sgf.node.unit
  (:use [maru.common.sgf.node.core] :reload)
  (:use [clojure.test]))

(deftest split-one-gtp-node
  (is (= (list "(;FF[4];GM[1])") (split "(;FF[4];GM[1])"))))

(deftest split-two-gtp-nodes
  (is (= (list "(;FF[4];GM[1])" "(;PB[Black];PW[White])") (split "(;FF[4];GM[1])(;PB[Black];PW[White])"))))

(deftest parse-node-from-sgf
  (is (= (list ";FF[4];GM[1]" "(;PB[Black];PW[White])") (parse "(;FF[4];GM[1](;PB[Black];PW[White]))"))))
