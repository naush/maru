(ns maru.common.board.core)

(def gray 0)

(def black 1)

(def white 2)

(def size 19)

(def board (vec (repeat (* size size) gray)))

(defn reset [size]
  (def size size)
  (def board (vec (repeat (* size size) gray))))

(defn to-pos [x y] (+ x (* y size)))

(defn to-x [pos] (mod pos size))

(defn to-y [pos] (int (/ pos size)))

(defn play [pos color] (assoc board pos color))

(defn play-black [pos] (assoc board pos black))

(defn play-white [pos] (assoc board pos white))
