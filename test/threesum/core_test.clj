(ns threesum.core-test
  (:use [midje.sweet])
  (:require [threesum.core :refer :all]))

(facts "about `squashable-pair?`"
       (fact "first 0 is always squashable"
             (squashable-pair? 0 :a) => true)
       (fact "second 0 is not squashable"
             (squashable-pair? :a 0) => false)
       (fact "1 and 2 can squash"
             (squashable-pair? 1 2) => true
             (squashable-pair? 2 1) => true)
       (fact "pairs of no less than 3 can squash"
             (squashable-pair? 3 3) => true
             (squashable-pair? 6 6) => true)
       (fact "pairs of 1 or 2 can't squash"
             (squashable-pair? 1 1) => false
             (squashable-pair? 2 2) => false)
       (fact "different numbers can't squash"
             (squashable-pair? 3 6) => false
             (squashable-pair? 6 12) => false))

(facts "about `squash-pair`"
       (fact "produces sum"
             (squash-pair 1 2) => 3)
       (fact "replaces 0 with number"
             (squash-pair 0 5) => 5)
       (fact "squashes 0s together"
             (squash-pair 0 0) => 0))

(facts "about `shift-row`"
       (fact "squashes first squashable pair of cells"
             (shift-row [:a :b :c :d] :next #(= [:a :b] [%1 %2]) (constantly :ab)) => [:ab :c :d :next]
             (shift-row [:a :b :c :d] :next #(= [:b :c] [%1 %2]) (constantly :bc)) => [:a :bc :d :next]
             (shift-row [:a :a :a :a] :next (constantly true) (constantly :aa)) => [:aa :a :a :next])
       (fact "replaces 0 in last cell"
             (shift-row [:a :b :c 0] :next (constantly false) (constantly 0)) => [:a :b :c :next])
       (fact "does not squash next into last cell"
             (shift-row [:a :b :c :d] :next #(= [:d :next] [%1 %2]) (constantly 0)) => [:a :b :c :d])
       (fact "does not squash if there are no squashable pairs"
             (shift-row [:a :b :c :d] :next (constantly false) (constantly 0)) => [:a :b :c :d]))

(facts "about `shiftable-row?`"
       (let [row [:a :b :c]]
         (fact "true when the row is different when squashed"
               (shiftable-row? row (constantly [:other]) 0 0) => true)
         (fact "false when the row is the same when squashed"
               (shiftable-row? row (constantly row) 0 0) => false)))

(facts "about `shiftable-board?`"
       (let [board [[:a1 :a2][:b1 :b2]]]
         (fact "true when a row is shiftable"
               (shiftable-board? board (constantly true)) => true)
         (fact "false when no rows are shiftable"
               (shiftable-board? board (constantly false)) => false)))

(facts "about `next-fillers`"
       (fact "empty when there are no spaces"
             (next-fillers 0 :next :empty) => [])
       (fact "contains next when there is space for 1"
             (next-fillers 1 :next :empty) => [:next])
       (fact "contains 1 next and the rest empty when there is space for 1"
             (next-fillers 5 :next :empty) => #(= 1 (count (filter #{:next} %1)))
             (next-fillers 5 :next :empty) => #(= 4 (count (filter #{:empty} %1)))))

(facts "about `shift-board`"
       (fact "shifts all rows"
             (shift-board [[1 2 3][4 5 6][7 8 9]] #(map inc %1)) => [[2 3 4][5 6 7][8 9 10]]))

