(ns maru.test.common.sgf.core
  (:use [maru.common.sgf.core] :reload)
  (:use [clojure.test]))

(deftest parse-gtp-node-with-empty-string
  (is (= {} (parse ""))))

(deftest parse-gtp-node-with-properties
  (is (= {:properties {:FF "4" :GM "1"} :next {} :variations ()} (parse "(;FF[4];GM[1])"))))

(deftest parse-gtp-node-without-properties
  (is (= {:properties {} :next {} :variations ()} (parse "()"))))

(deftest parse-gtp-node-with-next-node
  (is (= {:properties {:FF "4" :GM "1"}
          :next {:properties {:PB "Black" :PW "White"}
                 :next {}
                 :variations ()}
          :variations ()} (parse "(;FF[4];GM[1](;PB[Black];PW[White]))"))))

(deftest parse-gtp-node-with-next-node-and-single-variation
  (is (= {:properties {:FF "4" :GM "1"}
          :next {:properties {:FF "4" :GM "1"}
                 :next {}
                 :variations ()}
          :variations (list {:properties {:FF "4" :GM "1"}
                       :next {}
                       :variations ()})} (parse "(;FF[4];GM[1](;FF[4];GM[1])(;FF[4];GM[1]))"))))

(deftest parse-gtp-node-with-next-node-and-variations
  (is (= {:properties {:FF "4" :GM "1"}
          :next {:properties {:FF "4" :GM "1"}
                 :next {}
                 :variations ()}
          :variations (list
                      {:properties {:FF "4" :GM "1"}
                       :next {}
                       :variations ()}
                      {:properties {:FF "4" :GM "1"}
                       :next {}
                       :variations ()})} (parse "(;FF[4];GM[1](;FF[4];GM[1])(;FF[4];GM[1])(;FF[4];GM[1]))"))))

(deftest dump-moves-from-childless-node
  (is (= [{:B "ae"} {:W "bf"} {:B "ag"} {:W "bd"}]
         (dump-moves (parse "(;B[ae];W[bf];B[ag];W[bd])")))))

(deftest dump-moves-from-node-with-children
  (is (= [{:B "ae"} {:W "bf"} {:B "ag"} {:W "bd"}]
         (dump-moves (parse "(;B[ae];W[bf](;B[ag];W[bd]))")))))

(deftest dump-moves-from-node-with-set-stones-property
  (is (= [{:B "bb"} {:B "cb"} {:W "ww"} {:W "cc"} {:B "ae"} {:W "bf"}]
         (dump-moves (parse "(AB[bb][cb];AW[ww][cc];B[ae];W[bf])")))))

(deftest parse-file-with-invalid-file-path
  (is (= "File Not Found Exception"
         (try (parse-file "nil.sgf")
         (catch java.io.FileNotFoundException e "File Not Found Exception")))))

(deftest parse-file-with-missing-argument
  (is (= "Illegal Argument Exception"
         (try (parse-file)
         (catch IllegalArgumentException e "Illegal Argument Exception")))))
