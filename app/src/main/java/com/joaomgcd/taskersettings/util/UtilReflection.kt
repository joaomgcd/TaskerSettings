package com.joaomgcd.taskersettings.util

import com.joaomgcd.taskerbackcompat.util.withUppercaseFirstLetter
import java.lang.reflect.Method
import java.lang.reflect.Modifier

class ExceptionTaskerReflection(message: String) : RuntimeException(message)

class Functions<out Z>(val instance: Z?, val method: Method) {
    operator fun <R> invoke(): R {
        return method.invoke(instance) as R
    }

    operator fun <T, R> invoke(p1: T): R {
        return method.invoke(instance, p1) as R
    }

    operator fun <T1, T2, R> invoke(p1: T1, p2: T2): R {
        return method.invoke(instance, p1, p2) as R
    }

    operator fun <T1, T2, T3, R> invoke(p1: T1, p2: T2, p3: T3): R {
        return method.invoke(instance, p1, p2, p3) as R
    }
}

class Actions<out Z>(val instance: Z?, val method: Method) {
    operator fun invoke() {
        method.invoke(instance)
    }

    operator fun <T> invoke(p1: T) {
        method.invoke(instance, p1)
    }

    operator fun <T1, T2> invoke(p1: T1, p2: T2) {
        method.invoke(instance, p1, p2)
    }

    operator fun <T1, T2, T3> invoke(p1: T1, p2: T2, p3: T3) {
        method.invoke(instance, p1, p2, p3)
    }

    operator fun <T1, T2, T3, T4> invoke(p1: T1, p2: T2, p3: T3, p4: T4) {
        method.invoke(instance, p1, p2, p3, p4)
    }

    operator fun <T1, T2, T3, T4, T5> invoke(p1: T1, p2: T2, p3: T3, p4: T4, p5: T5) {
        method.invoke(instance, p1, p2, p3, p4, p5)
    }
}

private fun <M, Z> Class<Z>?.staticMethod(name: String, errorMessage: String? = null, getter: (Z?, Method) -> M): M {

    val clazz = this ?: throw ExceptionTaskerReflection("reflection class is null")
    return getter(null, clazz.declaredMethods.firstOrNull { it.name == name && Modifier.isStatic(it.modifiers) }
            ?: throw ExceptionTaskerReflection(errorMessage ?: "${clazz.name}:$name"))

}

private fun <M, Z : Any> Z?.method(name: String, errorMessage: String? = null, getter: (Z?, Method) -> M): M {
    val instance: Z = this ?: throw ExceptionTaskerReflection("reflection instance is null")
    val clazz = instance::class.java
    return getter(instance, clazz.declaredMethods.firstOrNull { it.name == name }
            ?: throw ExceptionTaskerReflection(errorMessage ?: "${clazz.name}:$name"))

}

fun <Z : Any> Z?.call(name: String, errorMessage: String? = null) = method(name, errorMessage) { instance, method -> Functions(instance, method) }
fun <Z : Any> Z?.run(name: String, errorMessage: String? = null) = method(name, errorMessage) { instance, method -> Actions(instance, method) }


fun <Z : Any> Class<Z>?.staticFunction(name: String, errorMessage: String? = null) = staticMethod(name, errorMessage) { instance, method -> Functions(instance, method) }
fun <Z : Any> Class<Z>?.staticAction(name: String, errorMessage: String? = null) = staticMethod(name, errorMessage) { instance, method -> Actions(instance, method) }

class PropertyAnnotationAndInstance<out TInstance : Any, out TAnnotation : Annotation>(val instance: TInstance, getter: Method, setter: Method?, annotation: TAnnotation) : PropertyAndAnnotation<TAnnotation>(getter, setter, annotation) {
    constructor(instance: TInstance, propertyAndAnnotation: PropertyAndAnnotation<TAnnotation>) : this(instance, propertyAndAnnotation.getter, propertyAndAnnotation.setter, propertyAndAnnotation.annotation)

    var property: Any?
        get() = getter(instance)
        set(value) {
            setter?.invoke(instance, value)
        }
}

open class PropertyAndAnnotation<out TAnnotation : Annotation>(val getter: Method, internal val setter: Method?, val annotation: TAnnotation) {
    val type: Class<*> = getter.returnType
}

open class MethodAndAnnotation<out TAnnotation : Annotation>(val method: Method, val annotation: TAnnotation)


//inline fun <reified TInstance : Any, reified TAnnotation : Annotation> TInstance.getPropertiesAndAnnotations(): List<PropertyAnnotationAndInstance<TInstance, TAnnotation>> {
//    val clzz = TInstance::class.java
//    return clzz.getPropertiesAndAnnotations<TAnnotation>().map { PropertyAnnotationAndInstance(this, it) }
//}

inline fun <TInstance : Any, reified TAnnotation : Annotation> Class<out TInstance>.getPropertiesAndAnnotations(instance: TInstance) = this.getPropertiesAndAnnotations(TAnnotation::class.java).map { PropertyAnnotationAndInstance(instance, it) }
fun <TAnnotation : Annotation> Class<*>.getPropertiesAndAnnotations(annotationClass: Class<TAnnotation>): List<PropertyAndAnnotation<TAnnotation>> {
    val methodsWithAnnotation = this.declaredMethods.filter { it.isAnnotationPresent(annotationClass) }
    val result = methodsWithAnnotation.map { method ->
        val name = method.name.replace("\$annotations", "").withUppercaseFirstLetter
        val getter = this.getMethod("get$name")
        val setter = try {
            this.getMethod("set$name", getter.returnType)
        } catch (ex: NoSuchMethodException) {
            null
        }
        val annotation = method.getAnnotation(annotationClass)
        PropertyAndAnnotation(getter, setter, annotation)
    }.toMutableList()
    this.superclass?.let { result.addAll(it.getPropertiesAndAnnotations(annotationClass)) }
    return result
}

fun <TAnnotation : Annotation> Class<*>.getAnnotationWithInheritance(annotationClass: Class<TAnnotation>): TAnnotation? {
    val annotation = getAnnotation(annotationClass)
    if (annotation != null) return annotation
    return this.superclass?.getAnnotationWithInheritance(annotationClass)
}

fun <TAnnotation : Annotation> Class<*>.getMethodsAndAnnotations(annotationClass: Class<TAnnotation>): List<MethodAndAnnotation<TAnnotation>> {
    val methodsWithAnnotation = this.declaredMethods.filter { it.isAnnotationPresent(annotationClass) }
    val result = methodsWithAnnotation.map { method ->
        val annotation = method.getAnnotation(annotationClass)
        MethodAndAnnotation(method, annotation)
    }.toMutableList()
    this.superclass?.let { result.addAll(it.getMethodsAndAnnotations(annotationClass)) }
    return result
}
//inline operator fun <R, reified Z> String.invoke(): () -> R {
//    val method = this.method<Z>()
//    return { method.invoke(this) as R }
//}
//
//operator fun <T, R> Method.invoke(p1: T): (T) -> R {
//    val method = this
//    return { method.invoke(this) as R }
//}

val Class<*>.allStaticFields
    get() = hashMapOf<String, Any>().apply {
        declaredFields.forEach {
            put(it.name, it.get(null))
        }
    }