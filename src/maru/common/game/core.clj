(ns maru.common.game.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.info.core :as info]))

(defn legal? [board x y color]
  (if (= info/ko (board/to-pos x y)) false true))
; pos is ko => false
; pos is out of bound => false
; pos is not empty => false
; pos can kill an adjacent enemy group => true
; pos is not suicide => true
