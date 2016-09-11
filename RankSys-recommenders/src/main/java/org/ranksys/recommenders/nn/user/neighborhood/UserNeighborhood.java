/* 
 * Copyright (C) 2015 Information Retrieval Group at Universidad Autónoma
 * de Madrid, http://ir.ii.uam.es
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.ranksys.recommenders.nn.user.neighborhood;

import java.util.stream.Stream;
import org.ranksys.core.index.fast.FastUserIndex;
import org.ranksys.core.util.tuples.Tuple2id;
import org.ranksys.core.util.tuples.Tuple2od;
import org.ranksys.recommenders.nn.neighborhood.Neighborhood;

/**
 * User neighborhood. Wraps a generic neighborhood and a fast user index.
 *
 * @author Saúl Vargas (saul.vargas@uam.es)
 * 
 * @param <U> type of the users
 */
public class UserNeighborhood<U> implements FastUserIndex<U> {

    /**
     * Fast user index.
     */
    protected final FastUserIndex<U> uIndex;

    /**
     * Generic fast neighborhood.
     */
    protected final Neighborhood neighborhood;

    /**
     * Constructor
     *
     * @param uIndex fast user index
     * @param neighborhood generic fast neighborhood
     */
    public UserNeighborhood(FastUserIndex<U> uIndex, Neighborhood neighborhood) {
        this.uIndex = uIndex;
        this.neighborhood = neighborhood;
    }

    public Neighborhood neighborhood() {
        return neighborhood;
    }

    @Override
    public int numUsers() {
        return uIndex.numUsers();
    }

    @Override
    public int user2uidx(U u) {
        return uIndex.user2uidx(u);
    }

    @Override
    public U uidx2user(int uidx) {
        return uIndex.uidx2user(uidx);
    }

    /**
     * Returns the neighborhood of a user/index.
     *
     * @param idx user/index whose neighborhood is calculated
     * @return stream of user/item-similarity pairs.
     */
    public Stream<Tuple2id> getNeighbors(int idx) {
        return neighborhood.getNeighbors(idx);
    }

    /**
     * Returns a stream of user neighbors
     *
     * @param u user whose neighborhood is returned
     * @return a stream of user-score pairs
     */
    public Stream<Tuple2od<U>> getNeighbors(U u) {
        return getNeighbors(user2uidx(u))
                .map(this::uidx2user);
    }
}
