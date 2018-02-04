/*
 * Copyright 2018 JamesZBL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package me.zbl.easyjson.io;

import me.zbl.easyjson.internal.StatusCheck;
import me.zbl.easyjson.io.lexical.JsonLexical;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

/**
 * 实现 Json 字符流的输入/输出
 *
 * @author JamesZBL
 * @date 2018-02-04
 */
public class JsonStringWriter implements Closeable, Flushable {

  private Writer mWriter;
  /**
   * 键值对之间的分隔符，默认冒号
   */
  private String delimiter = ":";
  /**
   * 键值对中的“键”
   */
  private String itemName;
  /**
   * 以栈的形式存储词法上下文
   */
  private JsonLexical[] contextStack = new JsonLexical[64];
  /**
   * 初始栈顶位置
   */
  private int contextStackSize = 0;

  public JsonStringWriter(Writer w) {
    StatusCheck.checkIfNull(w);
    this.mWriter = w;
  }



  /**
   * 对词法上下文栈进行压栈操作
   * 用于开辟新的作用域（一个新的数组或新的对象）
   *
   * @param lexical 词法（入栈元素）
   */
  private void stackPush(JsonLexical lexical) {
    if (contextStackSize == contextStack.length) {
      // 如果发现栈满，则创建一个2倍容量的栈，将原有元素拷贝到新栈中，并将原栈的引用指向新栈
      JsonLexical[] n = new JsonLexical[contextStack.length * 2];
      System.arraycopy(contextStack, 0, n, 0, contextStackSize);
      contextStack = n;
    }
    contextStack[contextStackSize++] = lexical;
  }

  /**
   * 获取栈顶元素
   */
  private JsonLexical stackLast() {
    if (contextStackSize == 0) {
      throw new IllegalStateException("读写已经完成，无法再获取词法元素");
    }
    return contextStack[contextStackSize - 1];
  }

  /**
   * 踢出栈顶元素，并将新元素压栈
   *
   * @param lexical 词法（新的栈顶元素）
   */
  private void stackKick(JsonLexical lexical) {
    contextStack[contextStackSize - 1] = lexical;
  }

  @Override
  public void close() throws IOException {

  }

  @Override
  public void flush() throws IOException {

  }
}
