(ns maru.test.common.gtp.core
  (:use [maru.common.gtp.core] :reload)
  (:refer-clojure :exclude [name])
  (:use [clojure.test]))

(deftest get-protocol-version
  (is (= "2" (protocol_version))))

(deftest set-and-get-name
  (make-name-command #(str "maru"))
  (is (= "maru" (name))))

(deftest set-and-get-version
  (make-version-command #(str "0.1"))
  (is (= "0.1" (version))))

(deftest get-commands
  (is (= "protocol_version\nname\nversion\nknown_command\nlist_commands\nquit\nboardsize\nclear_board\nkomi\nplay\ngenmove"
      (list_commands))))

(deftest given-known-command-returns-true
  (is (= true (known_command "protocol_version"))))

(deftest given-last-known-command-returns-true
  (is (= true (known_command "genmove"))))

(deftest given-unknown-command-returns-false
  (is (= false (known_command "unknown_command"))))

(deftest given-partial-command-returns-false
  (is (= false (known_command "ver"))))

(deftest implement-boardsize-and-return-boardize
  (make-boardsize-command #(str "boardsize " (first %)))
  (is (= "boardsize 1" (boardsize "1"))))

(deftest parse-input-with-no-args
  (make-version-command #(str ""))
  (is (= "=\n" (parse "version"))))

(deftest parse-input-with-arg
  (make-boardsize-command #(str "boardsize " (first %)))
  (is (= "= boardsize 19\n" (parse "boardsize 19"))))

(deftest parse-input-with-multiple-args
  (make-play-command #(str "play " (first %) " " (second %)))
  (is (= "= play B Q16\n" (parse "play B Q16"))))

(deftest parse-command-with-special-character
  (make-name-command "maru")
  (is (= "= true\n" (parse "known_command name"))))

(deftest parse-invalid-name
  (is (= "? command not found\n" (parse "suicide"))))

(deftest parse-unimplemented-command-name
  (is (= "? unimplemented\n" (parse "clear_board"))))

(deftest parse-command-with-illegal-arguments
  (make-version-command #(str "version"))
  (is (= "? command not found\n" (parse "version 1"))))
