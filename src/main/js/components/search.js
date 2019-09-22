import React from "react";
import {withRouter} from "react-router-dom";
import './search.scss'

class Search extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            searchVal: "",
            selectedCategory: "",
            categories: ["hi"]
        };

        this.onSearchNameChange = this.onSearchNameChange.bind(this);
        this.onCategoryChange = this.onCategoryChange.bind(this);
        this.onSearch = this.onSearch.bind(this);
    }

    onSearchNameChange(e) {
        const newVal = e.target.value;
        this.setState({searchVal: newVal});
    }

    onCategoryChange(e) {
        const newVal = e.target.value;

        this.setState({selectedCategory: newVal})
    }

    onSearch() {
        if (this.props.itemsCallback) {
            let {searchVal, selectedCategory} = this.state;
            const params = [{
                name: 'name',
                value: searchVal
            },{
                name: 'category',
                value: selectedCategory
            }];

            const queryParams = params
                .filter(p => !!p.value)
                .filter(p => !!p.value.trim())
                .map(p => `${p.name}=${p.value.trim()}`)
                .reduce((prev, cur) => (prev ? prev + "&" : "?") + cur, "");

            this.props.itemsCallback({
                loading: true,
                items: null
            });
            fetch('/api/grocery' + queryParams)
                .then(r => r.json())
                .then(r => this.props.itemsCallback({
                    loading: false,
                    items: r
                }))
        }
    }

    render() {
        const {searchVal, categories, selectedCategory} = this.state;
        return (
            <div className="search">
                <input onChange={this.onSearchNameChange} value={searchVal} placeholder={"Search by name"}/>
                <select onChange={this.onCategoryChange} value={selectedCategory}>
                    <option value={""}>None</option>
                    {categories.map((category, index) => <option key={index} value={category}>{category[0].toUpperCase() + category.slice(1).toLowerCase()}</option>)}
                </select>
                <button onClick={this.onSearch}>Search</button>
            </div>
        );
    }
}

export default withRouter(Search);