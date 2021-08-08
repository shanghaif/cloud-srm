package com.midea.cloud.common.utils.support;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder.Item;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.indices.TermsLookup;
import org.elasticsearch.script.Script;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * tanjl11
 */
public final class EsQuerySupport {
    private EsQuerySupport() {
    }

    public static MatchAllQueryBuilder matchAllQuery() {
        return new MatchAllQueryBuilder();
    }

    public static <T> MatchQueryBuilder matchQuery(SFunction<T> function, Object text) {
        return new MatchQueryBuilder(FieldParser.resolveFieldName(function), text);
    }

    public static <T> CommonTermsQueryBuilder commonTermsQuery(SFunction<T> function, Object text) {
        return new CommonTermsQueryBuilder(FieldParser.resolveFieldName(function), text);
    }

    public static <T> MultiMatchQueryBuilder multiMatchQuery(Object text, SFunction<T>... fieldNames) {
        return new MultiMatchQueryBuilder(text, FieldParser.resolveFieldNames(fieldNames));
    }

    public static <T> MatchPhraseQueryBuilder matchPhraseQuery(SFunction<T> function, Object text) {
        return new MatchPhraseQueryBuilder(FieldParser.resolveFieldName(function), text);
    }

    public static <T> MatchPhrasePrefixQueryBuilder matchPhrasePrefixQuery(SFunction<T> function, Object text) {
        return new MatchPhrasePrefixQueryBuilder(FieldParser.resolveFieldName(function), text);
    }

    public static DisMaxQueryBuilder disMaxQuery() {
        return new DisMaxQueryBuilder();
    }

    public static IdsQueryBuilder idsQuery() {
        return new IdsQueryBuilder();
    }

    public static IdsQueryBuilder idsQuery(String... types) {
        return (new IdsQueryBuilder()).types(types);
    }

    public static <T> TermQueryBuilder termQuery(SFunction<T> function, String value) {
        return new TermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> TermQueryBuilder termQuery(SFunction<T> function, int value) {
        return new TermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> TermQueryBuilder termQuery(SFunction<T> function, long value) {
        return new TermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> TermQueryBuilder termQuery(SFunction<T> function, float value) {
        return new TermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> TermQueryBuilder termQuery(SFunction<T> function, double value) {
        return new TermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> TermQueryBuilder termQuery(SFunction<T> function, boolean value) {
        return new TermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> TermQueryBuilder termQuery(SFunction<T> function, Object value) {
        return new TermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> FuzzyQueryBuilder fuzzyQuery(SFunction<T> function, String value) {
        return new FuzzyQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> FuzzyQueryBuilder fuzzyQuery(SFunction<T> function, Object value) {
        return new FuzzyQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> PrefixQueryBuilder prefixQuery(SFunction<T> function, String prefix) {
        return new PrefixQueryBuilder(FieldParser.resolveFieldName(function), prefix);
    }

    public static <T> RangeQueryBuilder rangeQuery(SFunction<T> function) {
        return new RangeQueryBuilder(FieldParser.resolveFieldName(function));
    }

    public static <T> WildcardQueryBuilder wildcardQuery(SFunction<T> function, String query) {
        return new WildcardQueryBuilder(FieldParser.resolveFieldName(function), query);
    }

    public static <T> RegexpQueryBuilder regexpQuery(SFunction<T> function, String regexp) {
        return new RegexpQueryBuilder(FieldParser.resolveFieldName(function), regexp);
    }

    public static <T> QueryStringQueryBuilder queryStringQuery(String queryString) {
        return new QueryStringQueryBuilder(queryString);
    }

    public static <T> SimpleQueryStringBuilder simpleQueryStringQuery(String queryString) {
        return new SimpleQueryStringBuilder(queryString);
    }

    public static <T> BoostingQueryBuilder boostingQuery(QueryBuilder positiveQuery, QueryBuilder negativeQuery) {
        return new BoostingQueryBuilder(positiveQuery, negativeQuery);
    }

    public static <T> BoolQueryBuilder boolQuery() {
        return new BoolQueryBuilder();
    }

    public static <T> SpanTermQueryBuilder spanTermQuery(SFunction<T> function, String value) {
        return new SpanTermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> SpanTermQueryBuilder spanTermQuery(SFunction<T> function, int value) {
        return new SpanTermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> SpanTermQueryBuilder spanTermQuery(SFunction<T> function, long value) {
        return new SpanTermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> SpanTermQueryBuilder spanTermQuery(SFunction<T> function, float value) {
        return new SpanTermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> SpanTermQueryBuilder spanTermQuery(SFunction<T> function, double value) {
        return new SpanTermQueryBuilder(FieldParser.resolveFieldName(function), value);
    }

    public static <T> SpanFirstQueryBuilder spanFirstQuery(SpanQueryBuilder match, int end) {
        return new SpanFirstQueryBuilder(match, end);
    }

    public static <T> SpanNearQueryBuilder spanNearQuery(SpanQueryBuilder initialClause, int slop) {
        return new SpanNearQueryBuilder(initialClause, slop);
    }

    public static <T> SpanNotQueryBuilder spanNotQuery(SpanQueryBuilder include, SpanQueryBuilder exclude) {
        return new SpanNotQueryBuilder(include, exclude);
    }

    public static <T> SpanOrQueryBuilder spanOrQuery(SpanQueryBuilder initialClause) {
        return new SpanOrQueryBuilder(initialClause);
    }

    public static <T> SpanWithinQueryBuilder spanWithinQuery(SpanQueryBuilder big, SpanQueryBuilder little) {
        return new SpanWithinQueryBuilder(big, little);
    }

    public static <T> SpanContainingQueryBuilder spanContainingQuery(SpanQueryBuilder big, SpanQueryBuilder little) {
        return new SpanContainingQueryBuilder(big, little);
    }

    public static <T> SpanMultiTermQueryBuilder spanMultiTermQueryBuilder(MultiTermQueryBuilder multiTermQueryBuilder) {
        return new SpanMultiTermQueryBuilder(multiTermQueryBuilder);
    }

    public static <T> FieldMaskingSpanQueryBuilder fieldMaskingSpanQuery(SpanQueryBuilder query, String field) {
        return new FieldMaskingSpanQueryBuilder(query, field);
    }

    public static <T> ConstantScoreQueryBuilder constantScoreQuery(QueryBuilder queryBuilder) {
        return new ConstantScoreQueryBuilder(queryBuilder);
    }

    public static <T> FunctionScoreQueryBuilder functionScoreQuery(QueryBuilder queryBuilder) {
        return new FunctionScoreQueryBuilder(queryBuilder);
    }

    public static <T> FunctionScoreQueryBuilder functionScoreQuery(QueryBuilder queryBuilder, FilterFunctionBuilder[] filterFunctionBuilders) {
        return new FunctionScoreQueryBuilder(queryBuilder, filterFunctionBuilders);
    }

    public static <T> FunctionScoreQueryBuilder functionScoreQuery(FilterFunctionBuilder[] filterFunctionBuilders) {
        return new FunctionScoreQueryBuilder(filterFunctionBuilders);
    }

    public static <T> FunctionScoreQueryBuilder functionScoreQuery(ScoreFunctionBuilder function) {
        return new FunctionScoreQueryBuilder(function);
    }

    public static <T> FunctionScoreQueryBuilder functionScoreQuery(QueryBuilder queryBuilder, ScoreFunctionBuilder function) {
        return new FunctionScoreQueryBuilder(queryBuilder, function);
    }

    public static <T> MoreLikeThisQueryBuilder moreLikeThisQuery(String[] fields, String[] likeTexts, Item[] likeItems) {
        return new MoreLikeThisQueryBuilder(fields, likeTexts, likeItems);
    }

    public static <T> MoreLikeThisQueryBuilder moreLikeThisQuery(String[] likeTexts, Item[] likeItems) {
        return moreLikeThisQuery(null, likeTexts, likeItems);
    }

    public static <T> MoreLikeThisQueryBuilder moreLikeThisQuery(String[] likeTexts) {
        return moreLikeThisQuery(null, likeTexts, null);
    }

    public static <T> MoreLikeThisQueryBuilder moreLikeThisQuery(Item[] likeItems) {
        return moreLikeThisQuery(null, null, likeItems);
    }

    public static <T> NestedQueryBuilder nestedQuery(String path, QueryBuilder query, ScoreMode scoreMode) {
        return new NestedQueryBuilder(path, query, scoreMode);
    }

    public static <T> TermsQueryBuilder termsQuery(SFunction<T> function, String... values) {
        return new TermsQueryBuilder(FieldParser.resolveFieldName(function), values);
    }

    public static <T> TermsQueryBuilder termsQuery(SFunction<T> function, int... values) {
        return new TermsQueryBuilder(FieldParser.resolveFieldName(function), values);
    }

    public static <T> TermsQueryBuilder termsQuery(SFunction<T> function, long... values) {
        return new TermsQueryBuilder(FieldParser.resolveFieldName(function), values);
    }

    public static <T> TermsQueryBuilder termsQuery(SFunction<T> function, float... values) {
        return new TermsQueryBuilder(FieldParser.resolveFieldName(function), values);
    }

    public static <T> TermsQueryBuilder termsQuery(SFunction<T> function, double... values) {
        return new TermsQueryBuilder(FieldParser.resolveFieldName(function), values);
    }

    public static <T> TermsQueryBuilder termsQuery(SFunction<T> function, Object... values) {
        return new TermsQueryBuilder(FieldParser.resolveFieldName(function), values);
    }

    public static <T> TermsQueryBuilder termsQuery(SFunction<T> function, Collection<?> values) {
        return new TermsQueryBuilder(FieldParser.resolveFieldName(function), values);
    }

    public static <T> WrapperQueryBuilder wrapperQuery(String source) {
        return new WrapperQueryBuilder(source);
    }

    public static <T> WrapperQueryBuilder wrapperQuery(BytesReference source) {
        return new WrapperQueryBuilder(source);
    }

    public static <T> WrapperQueryBuilder wrapperQuery(byte[] source) {
        return new WrapperQueryBuilder(source);
    }

    public static <T> TypeQueryBuilder typeQuery(String type) {
        return new TypeQueryBuilder(type);
    }

    public static <T> TermsQueryBuilder termsLookupQuery(SFunction<T> function, TermsLookup termsLookup) {
        return new TermsQueryBuilder(FieldParser.resolveFieldName(function), termsLookup);
    }

    public static <T> ScriptQueryBuilder scriptQuery(Script script) {
        return new ScriptQueryBuilder(script);
    }

    public static <T> GeoDistanceQueryBuilder geoDistanceQuery(SFunction<T> function) {
        return new GeoDistanceQueryBuilder(FieldParser.resolveFieldName(function));
    }

    public static <T> GeoBoundingBoxQueryBuilder geoBoundingBoxQuery(SFunction<T> function) {
        return new GeoBoundingBoxQueryBuilder(FieldParser.resolveFieldName(function));
    }

    public static <T> GeoPolygonQueryBuilder geoPolygonQuery(SFunction<T> function, List<GeoPoint> points) {
        return new GeoPolygonQueryBuilder(FieldParser.resolveFieldName(function), points);
    }

    public static <T> GeoShapeQueryBuilder geoShapeQuery(SFunction<T> function, ShapeBuilder shape) throws IOException {
        return new GeoShapeQueryBuilder(FieldParser.resolveFieldName(function), shape);
    }

    public static <T> GeoShapeQueryBuilder geoShapeQuery(SFunction<T> function, String indexedShapeId, String indexedShapeType) {
        return new GeoShapeQueryBuilder(FieldParser.resolveFieldName(function), indexedShapeId, indexedShapeType);
    }

    public static <T> GeoShapeQueryBuilder geoIntersectionQuery(SFunction<T> function, ShapeBuilder shape) throws IOException {
        GeoShapeQueryBuilder builder = geoShapeQuery(function, shape);
        builder.relation(ShapeRelation.INTERSECTS);
        return builder;
    }

    public static <T> GeoShapeQueryBuilder geoIntersectionQuery(SFunction<T> function, String indexedShapeId, String indexedShapeType) {
        GeoShapeQueryBuilder builder = geoShapeQuery(function, indexedShapeId, indexedShapeType);
        builder.relation(ShapeRelation.INTERSECTS);
        return builder;
    }

    public static <T> GeoShapeQueryBuilder geoWithinQuery(SFunction<T> function, ShapeBuilder shape) throws IOException {
        GeoShapeQueryBuilder builder = geoShapeQuery(function, shape);
        builder.relation(ShapeRelation.WITHIN);
        return builder;
    }

    public static <T> GeoShapeQueryBuilder geoWithinQuery(SFunction<T> function, String indexedShapeId, String indexedShapeType) {
        GeoShapeQueryBuilder builder = geoShapeQuery(function, indexedShapeId, indexedShapeType);
        builder.relation(ShapeRelation.WITHIN);
        return builder;
    }

    public static <T> GeoShapeQueryBuilder geoDisjointQuery(SFunction<T> function, ShapeBuilder shape) throws IOException {
        GeoShapeQueryBuilder builder = geoShapeQuery(function, shape);
        builder.relation(ShapeRelation.DISJOINT);
        return builder;
    }

    public static <T> GeoShapeQueryBuilder geoDisjointQuery(SFunction<T> function, String indexedShapeId, String indexedShapeType) {
        GeoShapeQueryBuilder builder = geoShapeQuery(function, indexedShapeId, indexedShapeType);
        builder.relation(ShapeRelation.DISJOINT);
        return builder;
    }

    public static <T> ExistsQueryBuilder existsQuery(SFunction<T> function) {
        return new ExistsQueryBuilder(FieldParser.resolveFieldName(function));
    }
    public static Header defalutHeaders(){
        return new BasicHeader("Content-Type", "application/json");
    }
}
