@file:Suppress("unused")

package by.shostko.rxvalidation

import io.reactivex.*

open class FlowableValidation<T> constructor(
        validator: (T) -> Completable,
        sourceProvider: () -> Flowable<T>
) : BaseValidation<T>(validator) {
    constructor(validator: Validator<T>, sourceProvider: () -> Flowable<T>) : this({ validator.validateAsCompletable(it) }, sourceProvider)

    override val value: Flowable<T> by lazy { sourceProvider().share() }
}

fun <T> Flowable<T>.validation(validator: Validator<T>): FlowableValidation<T> = FlowableValidation(validator) { this }

fun <T> Flowable<T>.validation(validator: (T) -> Completable): FlowableValidation<T> = FlowableValidation(validator) { this }

open class ObservableValidation<T> constructor(
        validator: (T) -> Completable,
        sourceProvider: () -> Observable<T>
) : BaseValidation<T>(validator) {
    constructor(validator: Validator<T>, sourceProvider: () -> Observable<T>) : this({ validator.validateAsCompletable(it) }, sourceProvider)

    override val value: Flowable<T> by lazy { sourceProvider().toFlowable(BackpressureStrategy.LATEST).share() }
}

fun <T> Observable<T>.validation(validator: Validator<T>): ObservableValidation<T> = ObservableValidation(validator) { this }

fun <T> Observable<T>.validation(validator: (T) -> Completable): ObservableValidation<T> = ObservableValidation(validator) { this }

open class SingleValidation<T> constructor(
        validator: (T) -> Completable,
        sourceProvider: () -> Single<T>
) : BaseValidation<T>(validator) {
    constructor(validator: Validator<T>, sourceProvider: () -> Single<T>) : this({ validator.validateAsCompletable(it) }, sourceProvider)

    override val value: Flowable<T> by lazy { sourceProvider().toFlowable().share() }
}

fun <T> Single<T>.validation(validator: Validator<T>): SingleValidation<T> = SingleValidation(validator) { this }

fun <T> Single<T>.validation(validator: (T) -> Completable): SingleValidation<T> = SingleValidation(validator) { this }

open class MaybeValidation<T> constructor(
        validator: (T) -> Completable,
        sourceProvider: () -> Maybe<T>
) : BaseValidation<T>(validator) {
    constructor(validator: Validator<T>, sourceProvider: () -> Maybe<T>) : this({ validator.validateAsCompletable(it) }, sourceProvider)

    override val value: Flowable<T> by lazy { sourceProvider().toFlowable().share() }
}

fun <T> Maybe<T>.validation(validator: Validator<T>): MaybeValidation<T> = MaybeValidation(validator) { this }

fun <T> Maybe<T>.validation(validator: (T) -> Completable): MaybeValidation<T> = MaybeValidation(validator) { this }