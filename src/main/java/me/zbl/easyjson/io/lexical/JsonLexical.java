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
package me.zbl.easyjson.io.lexical;

/**
 * Json 中的词法及作用域的定义
 *
 * @author JamesZBL
 * @date 2018-02-04
 */
public enum JsonLexical {

  /**
   * 空的 Json
   */
  DOCUMENT_WITHOUT_ANY_ELEMENTS,
  /**
   * 非空的 Json
   */
  DOCUMENT_WITH_ELEMENTS,
  /**
   * 空的 Json 数组
   */
  ARRAY_WITHOUT_ELEMENT,
  /**
   * 非空的 Json 数组
   */
  ARRAY_WITH_ELEMENTS,
  /**
   * 空的 Json 对象
   */
  OBJECT_WITHOUT_ATTRIBUT,
  /**
   * 非空的 Json 对象
   */
  OBJECT_WITH_ATRRIBUTES,
  /**
   * 作为键值对中的 “值” 的对象
   */
  OBJECT_AS_VALUE,
  /**
   * 读/写完毕
   */
  DOCUMENT_ENDED
}
