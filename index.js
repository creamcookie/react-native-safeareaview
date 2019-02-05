
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
    Dimensions, StatusBar
} from 'react-native';
import type { ViewStyleProp } from 'react-native/Libraries/StyleSheet/StyleSheet';
import {SAProps} from "react-native-ccs-safeareaview";

const { RNCSafeAreaView } = NativeModules;

type Props<T> = SAProps & { };

type State = {|
|};

const dim = Dimensions.get("screen");

export default class SafeAreaView<T: *> extends React.Component<Props<T>, State> {

    static propTypes = {
    };

    static defaultProps = {
        tintLight: false,
    };

    _beforeTintLight = false;

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

    componentDidMount(): void {
        if (Platform.OS == 'android') {
            RNCSafeAreaView.getTintLight().then(e => {
                this._beforeTintLight = e;
                RNCSafeAreaView.setTintLight(this.props.tintLight);
            });
        }
        else {
            StatusBar.setBarStyle(this.props.tintLight ? "light-content" : "dark-content", true);
        }
    }

    componentWillUnmount(): void {
        if (Platform.OS == 'android') {
            RNCSafeAreaView.setTintLight(this._beforeTintLight);
        }
        else {
            StatusBar.setBarStyle(this._beforeTintLight ? "light-content" : "dark-content", true);
        }
    }

    shouldComponentUpdate(nextProps: Readonly<P>, nextState: Readonly<S>, nextContext: any): boolean {
        if (this.props.tintLight != nextProps.tintLight) {
            RNCSafeAreaView.setTintLight(nextProps.tintLight);
        }
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
