/* 
 * Copyright (C) 2015 Information Retrieval Group at Universidad Autónoma
 * de Madrid, http://ir.ii.uam.es
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package es.uam.eps.ir.ranksys.novelty.sales.reranking;

import es.uam.eps.ir.ranksys.novdiv.itemnovelty.reranking.ItemNoveltyReranker;
import es.uam.eps.ir.ranksys.novelty.sales.ISDCItemNovelty;

/**
 * Inter-System Discovery Complement re-ranker.
 *
 * S. Vargas. Novelty and diversity evaluation and enhancement in Recommender
 * Systems. PhD Thesis.
 * 
 * @author Saúl Vargas (saul.vargas@uam.es)
 * @author Pablo Castells (pablo.castells@uam.es)
 * 
 * @param <U> type of the users
 * @param <I> type of the items
*/
public class ISDCItemNoveltyReranker<U, I> extends ItemNoveltyReranker<U, I> {

    /**
     * Constructor.
     *
     * @param lambda trade-off between relevance and novelty
     * @param novelty item novelty model
     * @param norm normalize the relevance and novelty scores
     */
    public ISDCItemNoveltyReranker(double lambda, ISDCItemNovelty<U, I> novelty, boolean norm) {
        super(lambda, novelty, norm);
    }
}
