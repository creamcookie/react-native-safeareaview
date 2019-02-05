
//
// export default RNCcsSafeareaview;


/* @flow */

import * as React from 'react';
import PropTypes from 'prop-types';
import {
    Animated,
    View,
    StyleSheet,
    ViewProps,
    Platform,
    SafeAreaView as RNSafeArea,
    NativeModules,
    Dimensions
} from 'react-native';
import type { ViewStyleProp } from 'react-native/Libraries/StyleSheet/StyleSheet';

const { RNCSafeAreaView } = NativeModules;

type Props<T> = ViewProps & {
};

type State = {|
|};

const dim = Dimensions.get("screen");

export default class SafeAreaView<T: *> extends React.Component<Props<T>, State> {

    static propTypes = { };

    static defaultProps = { };

    constructor(props: P, context: any) {
        super(props, context);
    }

    _onLayout() {
        if (Platform.OS !== 'android') return;
        this._el?.measure( (fx, fy, width, height, px, py) => {
            RNCSafeAreaView.getPadding()
                .then(size => {
                    let applySize = {};
                    applySize.top = (fy == 0 && py == fy) ? size.top : 0;
                    applySize.left = (fx == 0 && px == fx) ? size.left : 0;
                    applySize.right = (dim.width - 1 <= (px + width)) ? size.right : 0;
                    applySize.bottom = (dim.height - 1 <= (py + height)) ? size.bottom : 0;
                    this.setState({ applySize });
                })
                .catch(e => {

                })

        });
    }

    render() {

        const { style, onLayout, children, ...props } = this.props;

        const Element = Platform.OS == 'ios' ? RNSafeArea : View;
        let safePadder = { paddingLeft: 0, paddingRight: 0, paddingBottom: 0, paddingTop: 0 };
        if (Platform.OS == 'android' && this.state?.applySize) {
            safePadder.paddingTop = this.state.applySize.top;
            safePadder.paddingLeft = this.state.applySize.left;
            safePadder.paddingRight = this.state.applySize.right;
            safePadder.paddingBottom = this.state.applySize.bottom;
        }

        return (
            <Element
                {...props}
                ref={(_el) => this._el = _el}
                onLayout={(e) => { this._onLayout(); if (onLayout) onLayout(e); }}
                style={[ styles.container, style, safePadder ]} >
                { children }
            </Element>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        overflow: 'hidden',
    },
});
