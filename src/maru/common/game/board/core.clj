(ns maru.common.game.board.core
  (:refer-clojure :exclude [empty])
  (:require [maru.common.utility.core :as utility]))

(def gray 0)

(def black 1)

(def white 2)

(def size 19)

(defn string-to-color [string]
  (cond
    (or (= "b" string) (= "B" string)) black
    (or (= "w" string) (= "W" string)) white
    :else gray))

(def empty (vec (repeat (* size size) gray)))

(defn reset [size]
  (def size size)
  (def empty (vec (repeat (* size size) gray))))

(defn to-pos [x y] (+ x (* (- size y) size)))

(defn to-x [pos] (rem pos size))

(defn to-y [pos] (- (dec size) (int (/ pos size))))

(defn set-stone [board pos color] (assoc board pos color))

(defn set-black [board pos] (set-stone board pos black))

(defn set-white [board pos] (set-stone board pos white))

(defn color [board pos] (nth board pos))

(defn out-of-bound [x y]
  (let [size (dec size)]
    (or (< x 0) (> x size) (< y 0) (> y size))))

(defn find-neighbors [pos]
  (let [x (to-x pos)
        y (int (/ pos size))
        north [x (dec y)]
        east [(inc x) y]
        south [x (inc y)]
        west [(dec x) y]]
    (set (map #(+ (first %1) (* (second %1) size))
      (filter #(not (out-of-bound (first %1) (second %1)))
        (list north east south west))))))

(defn pos-from-string [string]
  (let [x (utility/letter-to-digit (first string))
        y (utility/string-to-digit (utility/remove-string-upto string 1))]
  (to-pos x y)))

(defn string-from-pos [pos]
  (let [x (to-x pos)
        y (to-y pos)]
  (str (utility/digit-to-letter x) (inc y))))
