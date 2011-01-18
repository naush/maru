(ns maru.common.sgf.node
  (:require [maru.common.sgf.pattern :as pattern])
  (:require [maru.common.utility.core :as utility]))

(def lparen "(")
(def rparen ")")

(defn split [sgf]
  (loop [source sgf index 0 lparens 0 rparens 0 nodes ()]
    (if (empty? source) nodes
    (let [char (utility/read-char-as-string source index)]
      (cond (= char lparen) (recur source (inc index) (inc lparens) rparens nodes)
            (= char rparen) (if (= lparens (inc rparens))
                  (let [node (first (utility/split-string-at source index))
                        nodes (utility/insert-last nodes node)
                        source (utility/remove-string-upto source (.length node))]
                  (recur source 0 0 0 nodes))
                  (recur source (inc index) lparens (inc rparens) nodes))
            :else (recur source (inc index) lparens rparens nodes))))))

(defn create [properties next variations]
  (hash-map :properties properties
            :next next
            :variations variations))

(defn parse [sgf] (rest (re-find pattern/node sgf)))
