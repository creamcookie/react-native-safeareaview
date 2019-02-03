using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Ccs.Safeareaview.RNCcsSafeareaview
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNCcsSafeareaviewModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNCcsSafeareaviewModule"/>.
        /// </summary>
        internal RNCcsSafeareaviewModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNCcsSafeareaview";
            }
        }
    }
}
