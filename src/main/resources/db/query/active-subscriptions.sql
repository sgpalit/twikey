SELECT s
FROM tw_subscription s
LEFT JOIN tw_payment p on (p.subscription_id = s.id AND p.month=:month AND p.year=:year)
WHERE s.status='ACTIVE' AND s.next_payment_date < :process_date AND p.id IS NULL;

-- we see in the explain plan before adding indexes the cost and in query plan which fields are used to filter the records
-- according to this result we decide to add indexes.