(ns maru.common.game.board.core
  (:refer-clojure :exclude [empty])
  (:require [maru.common.game.color.core :as color])
  (:require [maru.common.utility.core :as utility]))

(defn empty [size] (vec (repeat (* size size) color/open)))

(defn to-point [x y size] (+ x (* y size)))

(defn to-x [point size] (rem point size))

(defn to-y [point size] (int (/ point size)))

(defn set-stone [board point color] (assoc board point color))

(defn set-black [board point] (set-stone board point color/black))

(defn set-white [board point] (set-stone board point color/white))

(defn remove-stone [board point] (assoc board point color/open))

(defn set-stones [board stones] (reduce #(set-stone %1 (:point %2) (:color %2)) board stones))

(defn remove-stones [board stones]
  (if (empty? stones) board
    (reduce #(remove-stone %1 %2) board stones)))

(defn color [board point] (nth board point))

(defn open? [board x y size] (= color/open (nth board (to-point x y size))))

(defn out-of-bound [x y size]
  (let [size (dec size)]
    (or (< x 0) (> x size) (< y 0) (> y size))))

(defn find-neighbors [point size]
  (let [x (to-x point size)
        y (int (/ point size))
        north [x (dec y)]
        east [(inc x) y]
        south [x (inc y)]
        west [(dec x) y]]
    (set (map #(+ (first %) (* (second %) size))
      (filter #(not (out-of-bound (first %) (second %) size))
        (list north east south west))))))
