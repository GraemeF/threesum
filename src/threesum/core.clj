(ns threesum.core)

(def board [[nil nil  3  nil]
            [ 3   2  nil nil]
            [nil  1   3   3 ]
            [ 1  nil  2   1 ]])

(defn format-row [row]
  (map #(if (nil? %)
          "    "
          (format "%04d" %))
       row))

(defn print-board [board]
  (doseq [row board]
    (println (format-row row))))

(defn squashable? [a b]
  (or (nil? a)
      (and
        (not (nil? b))
        (or (and (> a 2) (= a b))
            (= 3 (+ a b))))))

(defn squash [a b]
  (if (nil? b)
    nil
    (+ (or a 0) b)))

(defn first-squashable-pair [row squashable?]
  (if (squashable? (first row) (second row))
    [(first row) (second row)]
    (recur (rest row) squashable?)))

(defn shift [row next squashable? squash]
  (let [shifted (conj row next)]
    (if (squashable? (first row) (second row))
      (into [(squash (first row) (second row))] (rest (rest shifted)))
      shifted)))

;;(print-board board)

(defn -main []
  (print-board board))
