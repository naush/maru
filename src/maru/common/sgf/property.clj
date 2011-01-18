(ns maru.common.sgf.property
  (:require [maru.common.sgf.pattern :as pattern])
  (:require [maru.common.utility.core :as utility])
  (:require [clojure.contrib.string :only [partition] :as string]))

(defn append-value [value values] (str "[" value "]" values))

(defn parse-value [key values]
  (if (empty? values) []
  (vec (map #(hash-map key (second %))
    (utility/even-terms (string/partition pattern/property-value values))))))

(defn append-moves [properties color moves]
  (assoc properties :MV (clojure.set/union (:MV properties) (parse-value color moves))))

(defn add [properties key value values]
  (cond
    (or (= key "AW") (= key "W")) (append-moves properties :W (append-value value values))
    (or (= key "AB") (= key "B")) (append-moves properties :B (append-value value values))
    :else (assoc properties (keyword key) value)))

(defn parse [stream]
  (loop [tokens (rest (string/partition pattern/property stream)) properties {}]
    (if (empty? tokens) properties
    (let [key-value (rest (first tokens))
          key (first key-value)
          value (second key-value)
          values (second tokens)]
          (recur (nnext tokens) (add properties key value values))))))
