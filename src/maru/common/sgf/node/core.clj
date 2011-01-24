(ns maru.common.sgf.node.core
  (:require [maru.common.sgf.pattern.core :as pattern])
  (:use [maru.common.utility.core :only
  [read-char-as-string insert-last remove-string-upto split-string-at]]))

(def lparen "(")
(def rparen ")")

(defn split [sgf]
  (loop [source sgf index 0 lparens 0 rparens 0 nodes ()]
    (if (empty? source) nodes
    (let [char (read-char-as-string source index)]
      (cond (= char lparen) (recur source (inc index) (inc lparens) rparens nodes)
            (= char rparen) (if (= lparens (inc rparens))
                  (let [node (first (split-string-at source index))
                        nodes (insert-last nodes node)
                        source (remove-string-upto source (.length node))]
                  (recur source 0 0 0 nodes))
                  (recur source (inc index) lparens (inc rparens) nodes))
            :else (recur source (inc index) lparens rparens nodes))))))

(defn create [properties next variations]
  {:properties properties
   :next next
   :variations variations})

(defn parse [sgf] (rest (re-find pattern/node sgf)))
