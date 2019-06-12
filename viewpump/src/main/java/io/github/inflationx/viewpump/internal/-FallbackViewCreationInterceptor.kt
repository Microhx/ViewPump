@file:JvmName("-FallbackViewCreationInterceptor")
package io.github.inflationx.viewpump.internal

import android.util.Log
import io.github.inflationx.viewpump.InflateResult
import io.github.inflationx.viewpump.Interceptor
import io.github.inflationx.viewpump.Interceptor.Chain
import io.github.inflationx.viewpump.ViewPump

@Suppress("ClassName")
internal class `-FallbackViewCreationInterceptor` : Interceptor {

  override fun intercept(chain: Chain): InflateResult {
    val request = chain.request()
    val viewCreator = request.fallbackViewCreator
    val fallbackView =

    if(ViewPump.get().recordViewCreatedTime) {

      val startTime = System.currentTimeMillis()
      val onCreateView = viewCreator.onCreateView(request.parent, request.name, request.context, request.attrs)
      val endTime = System.currentTimeMillis();

      onCreateView?.apply {
        if(this.id > 0) {
          val entryName = request.context.resources.getResourceEntryName(this.id)
          Log.d("createViewTime","${request.name}[$entryName] created cost ${endTime - startTime} + ms")
        }
      }

      onCreateView

    }else {

      viewCreator.onCreateView(request.parent, request.name, request.context, request.attrs)
    }


    return InflateResult(
        view = fallbackView,
        name = fallbackView?.javaClass?.name ?: request.name,
        context = request.context,
        attrs = request.attrs
    )
  }
}
