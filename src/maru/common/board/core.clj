(ns maru.common.board.core (:refer-clojure :exclude [empty]))

(def gray 0)

(def black 1)

(def white 2)

(def size 19)

(def empty (vec (repeat (* size size) gray)))

(defn reset [size]
  (def size size)
  (def empty (vec (repeat (* size size) gray))))

(defn to-pos [x y] (+ x (* y size)))

(defn to-x [pos] (mod pos size))

(defn to-y [pos] (int (/ pos size)))

(defn play [board pos color] (assoc board pos color))

(defn play-black [board pos] (play board pos black))

(defn play-white [board pos] (play board pos white))
