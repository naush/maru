(ns maru.common.gtp.unit
  (:use [maru.common.gtp.core] :reload)
  (:refer-clojure :exclude [name])
  (:use [clojure.test]))

(deftest get-protocol-version
  (is (= "2" (:message (protocol_version)))))

(deftest set-and-get-name
  (make-name-command #(str "maru"))
  (is (= "maru" (name))))

(deftest set-and-get-version
  (make-version-command #(str "0.1"))
  (is (= "0.1" (version))))

(deftest get-commands
  (is (= "protocol_version\nname\nversion\nknown_command\nlist_commands\nquit\nboardsize\nclear_board\nkomi\nplay\ngenmove"
      (:message (list_commands)))))

(deftest given-known-command-returns-true
  (is (= true (:message (known_command "protocol_version")))))

(deftest given-last-known-command-returns-true
  (is (= true (:message (known_command "genmove")))))

(deftest given-unknown-command-returns-false
  (is (= false (:message (known_command "unknown_command")))))

(deftest given-partial-command-returns-false
  (is (= false (:message (known_command "ver")))))

(deftest implement-boardsize-and-return-boardize
  (make-boardsize-command #(str "boardsize " (first %)))
  (is (= "boardsize 1" (boardsize "1"))))

(deftest parse-input-with-no-args
  (make-version-command #(hash-map :message ""))
  (is (= "=\n" (:message (parse "version")))))

(deftest parse-input-with-arg
  (make-boardsize-command #(hash-map :message (str "boardsize " (first %))))
  (is (= "= boardsize 19\n" (:message (parse "boardsize 19")))))

(deftest parse-input-with-multiple-args
  (make-play-command #(hash-map :message (str "play " (first %) " " (second %))))
  (is (= "= play B Q16\n" (:message (parse "play B Q16")))))

(deftest parse-command-with-special-character
  (make-name-command "maru")
  (is (= "= true\n" (:message (parse "known_command name")))))

(deftest parse-invalid-name
  (is (= "? command not found\n" (:message (parse "suicide")))))

(deftest parse-unimplemented-command-name
  (is (= "? unimplemented\n" (:message (parse "clear_board")))))

(deftest parse-command-with-illegal-arguments
  (make-version-command #(hash-map :message "version"))
  (is (= "? command not found\n" (:message (parse "version 1")))))
