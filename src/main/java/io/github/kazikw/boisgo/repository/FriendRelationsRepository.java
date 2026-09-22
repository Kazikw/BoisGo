package io.github.kazikw.boisgo.repository;

import io.github.kazikw.boisgo.entity.FriendRelations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRelationsRepository extends JpaRepository<FriendRelations, Long> {
    @Query("""
    SELECT CASE WHEN COUNT(fr) > 0 THEN true ELSE false END
    FROM FriendRelations fr
    WHERE fr.userA.id = :idA AND fr.userB.id = :idB
""")
    boolean friends(@Param("idA") Long idA,
                    @Param("idB") Long idB);


}
