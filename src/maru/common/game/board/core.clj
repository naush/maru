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

(defn play [board pos color] (assoc board pos color))

(defn play-black [board pos] (play board pos black))

(defn play-white [board pos] (play board pos white))

(defn color [board pos] (nth board pos))

(defn out-of-bound [pos]
  (let [size (dec size)
	    x (to-x pos)
        y (to-y pos)]
    (if (or (< x 0) (> x size)
            (< y 0) (> y size))
      true false)))

(defn pos-from-string [string]
  (let [x (utility/letter-to-digit (first string))
        y (utility/string-to-digit (utility/remove-string-upto string 1))]
  (to-pos x y)))
